package edu.eci.ieti.taskplannerapp.network.service;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

import edu.eci.ieti.taskplannerapp.network.dto.LoginDto;
import edu.eci.ieti.taskplannerapp.network.dto.TokenDto;

public interface AuthService {

    @POST("auth")
    Observable<TokenDto> auth(@Body LoginDto loginDto);

}