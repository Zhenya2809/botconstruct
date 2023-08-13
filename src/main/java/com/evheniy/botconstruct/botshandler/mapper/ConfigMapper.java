package com.evheniy.botconstruct.botshandler.mapper;

import com.evheniy.botconstruct.model.BotUser;
import com.evheniy.botconstruct.model.BotsData;
import com.evheniy.botconstruct.model.Message;
import com.evheniy.botconstruct.model.dto.BotUserDto;
import com.evheniy.botconstruct.model.dto.BotsDataDto;
import com.evheniy.botconstruct.model.dto.MessageDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConfigMapper {

    ConfigMapper INSTANCE = Mappers.getMapper(ConfigMapper.class);

    default MessageDto toMessageDto(Message message) {
        return MessageDto.fromMessage(message);
    }

    List<MessageDto> toMessageDto(List<Message> messagesList);

//    List<BotUserDto> toBotUserDto(List<BotUser> botUserList);
//
//    List<BotsDataDto> toBotsDataDto(List<BotsData> botsDataList);


}
