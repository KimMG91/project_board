package Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "set")
public class ResponseDto<D> {
    private boolean result;
    private String message;
    private D data;

    public static <D> ResponseDto<D> setSuccess(String message){
        return ResponseDto.set(true, message, null);
    }

    public static <D> ResponseDto<D> setFailed(String message){
        return ResponseDto.set(false, message, null);
    }

    public static <D> ResponseDto<D> setSuccessData(String message, D date){
        return ResponseDto.set(true, message, date);
    }

    public static <D> ResponseDto<D> setFaildeData(String message, D date){
        return ResponseDto.set(false, message, date);
    }
}
