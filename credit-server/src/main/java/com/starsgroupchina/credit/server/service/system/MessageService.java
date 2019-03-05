package com.starsgroupchina.credit.server.service.system;

import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.common.context.ContextHolder;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.model.Member;
import com.starsgroupchina.credit.bean.model.Message;
import com.starsgroupchina.credit.bean.model.MessageExample;
import com.starsgroupchina.credit.key.MessageTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 14:48 2018/7/30
 * @Modifed By:
 */
@Service
public class MessageService extends AbstractService<Message, MessageExample> {

    @Autowired
    private MemberService memberService;

    public void sendMessage(Integer memberId,String projectNo,String title,MessageTemplate template){
        String content = template.getContent();
        Member member = memberService.getById(memberId);
        AuthMember authMember= (AuthMember) ContextHolder.getContext().getData();
        content = cast(content, authMember.getMember());
        content = cast(content,projectNo);
        Message message = new Message();
        message.setReceiveUserId(memberId);
        message.setReceiveUserName(member.getName());
        message.setTitle(title);
        message.setContent(content);
        this.create(message);
    }

    private String cast(String content,Object object){
        String name = content.substring(content.indexOf("{")+1, content.indexOf("}"));
        if (object instanceof String){
            return content.replace("{"+name+"}",(String)object);
        }
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            PropertyDescriptor pd = null;
            try {
                pd = new PropertyDescriptor(fieldName, clazz);
                Method readMethod = pd.getReadMethod();
                String value = readMethod.invoke(object)+"";
                content = content.replace("{" + fieldName + "}", value);
            } catch (Exception e) {
                continue;
            }
        }
        return content;
    }
}
