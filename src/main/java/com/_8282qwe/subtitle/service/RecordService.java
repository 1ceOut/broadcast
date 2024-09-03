package com._8282qwe.subtitle.service;

import io.livekit.server.EgressServiceClient;
import livekit.LivekitEgress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class RecordService {

    @Value("${livekit.api.key}")
    private String LIVEKIT_API_KEY;

    @Value("${livekit.api.secret}")
    private String LIVEKIT_API_SECRET;

    public void recordStart(String roomName) {
        System.out.println("record start!");

        LivekitEgress.EncodedFileOutput output = LivekitEgress.EncodedFileOutput.newBuilder()
                .setFileType(LivekitEgress.EncodedFileType.MP4)
                .setFilepath(roomName + LocalDateTime.now())
                .setS3(
                        LivekitEgress.S3Upload.newBuilder()
                                .setAccessKey("minioadmin")
                                .setSecret("jsPN5ClDgcOW9TRjlRK5KLp08z0FqXQk3Y48n6RncwD")
                                .setBucket("openvidu")
                                .setEndpoint("https://openvidu.midichi.kro.kr:9000")
                                .build()
                )
                .build();

        EgressServiceClient egressClient = EgressServiceClient.createClient("https://openvidu.midichi.kro.kr", LIVEKIT_API_KEY, LIVEKIT_API_SECRET);
        Call<LivekitEgress.EgressInfo> call = egressClient.startWebEgress(
                "https://reacticeout.icebuckwheat.kro.kr/liveroom/"+roomName+"/"+roomName,
                output);

        try {
            LivekitEgress.EgressInfo egressInfo = call.execute().body();
            System.out.println(egressInfo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
