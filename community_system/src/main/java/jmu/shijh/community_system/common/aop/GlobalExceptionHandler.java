package jmu.shijh.community_system.common.aop;

import com.github.pagehelper.PageException;

import jmu.shijh.community_system.common.exception.CustomException;
import jmu.shijh.community_system.common.exception.ParamCheckException;
import jmu.shijh.community_system.common.util.Cl;
import jmu.shijh.community_system.common.util.JsonResp;
import jmu.shijh.community_system.common.util.R;
import org.springframework.web.bind.annotation.*;

import javax.crypto.interfaces.PBEKey;
import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public JsonResp sqlError(SQLException e) {
        return R.error()
                .msg("提交数据存在异常")
                .help(e.getMessage())
                .build();
    }

    @ExceptionHandler
    public JsonResp pageException(PageException e) {
        return R.error()
                .msg("分页信息有误")
                .help(e.getMessage())
                .build();
    }

    @ExceptionHandler
    public JsonResp paramError(ParamCheckException e) {
        return R.error()
                .help("检查参数是否缺少")
                .msg(e.getMessage())
                .build();
    }

    @ExceptionHandler
    public JsonResp ioe(IOException e) {
        return R.error()
                .msg("请求出错了")
                .help(e.getMessage())
                .build();
    }

    @ExceptionHandler
    public JsonResp customError(CustomException e)   {
        return R.error()
                .msg(e.getMessage())
                .data(Cl.map().add("errorData", e.getErrorData()).build())
                .help(e.getErrorData() == null ? null: "请参考errorData")
                .build();
    }
}
