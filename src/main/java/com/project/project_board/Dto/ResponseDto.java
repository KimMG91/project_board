package com.project.project_board.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "set")
public class ResponseDto<D> {
    private boolean result;
    private String message;
    private D data;

    
    //성공 Return
    public static <D> ResponseDto<D> setSuccess(String message){
        return ResponseDto.set(true, message, null);
    }

    //실패 Return
    public static <D> ResponseDto<D> setFailed(String message){
        return ResponseDto.set(false, message, null);
    }

    //선공 Return + Data
    public static <D> ResponseDto<D> setSuccessData(String message, D date){
        return ResponseDto.set(true, message, date);
    }

    //실패 Return + Data
    public static <D> ResponseDto<D> setFaildeData(String message, D date){
        return ResponseDto.set(false, message, date);
    }

    //요청 성공 여부 확인
    public boolean getResult(){
        return result;
    }
}
