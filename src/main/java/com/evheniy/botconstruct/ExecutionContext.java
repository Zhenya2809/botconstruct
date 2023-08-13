package com.evheniy.botconstruct;

import com.evheniy.botconstruct.Service.BotsDataService;
import com.evheniy.botconstruct.Service.ReplyService;
import lombok.Data;

@Data
public class ExecutionContext {
    //    private final GlobalUser globalUser;
    private final ReplyService replyService;
}
