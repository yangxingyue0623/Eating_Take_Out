package com.yang.reggie.service.imp;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.reggie.com.R;
import com.yang.reggie.entity.OrderDetail;
import com.yang.reggie.entity.User;
import com.yang.reggie.mapper.UserMapper;
import com.yang.reggie.service.OrderDetailService;
import com.yang.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private OrderDetailService orderDetailService;
    @Resource
    private RedisTemplate redisTemplate;
    //把yml配置的邮箱号赋值到from

    @Value("${spring.mail.username}")
    private String from;
    //发送邮件需要的对象
    @Resource
    private JavaMailSender javaMailSender;
    //邮件发送人

    public void sendMsg1(String to, String subject, String text) {
        //发送简单邮件，简单邮件不包括附件等别的
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        //发送邮件
        javaMailSender.send(message);
    }


    @Override
    public R<String> sendMsg(User user, HttpSession session) {

        //1.获取邮箱号
        String email = user.getPhone();
        String subject = "Eatting外卖";
        //2.StringUtils.isNotEmpty字符串非空判断
        if (StringUtils.isNotEmpty(email)) {
            //3.发送验证码
            String code = RandomUtil.randomNumbers(6);
            ;
            String text = "【Eatting外卖】您好，您的登录验证码为：" + code + "，请尽快登录，如非本人操作，请忽略此邮件。";
            log.info("验证码为：" + code);
//            //4.发送邮件
//            this.sendMsg1(email, subject, text);
            //将验证码保存到session当中
            //将邮箱作为key，将code最为value保存到session中,，因此邮箱和验证码可以一一对
            // 5.保存生成的验证码到redis,5分钟失效
            redisTemplate.opsForValue().set("LOGIN_CODE_KEY" + email, code, 5, TimeUnit.MINUTES);
            return R.success("验证码发送成功");
        }
        return R.error("验证码发送异常，请重新发送");
    }

    @Override
    public R<User> login(Map map, HttpSession session) {


        log.info(map.toString());
        //1.获取手机号（邮箱）
        String phone = map.get("phone").toString();
        //2.获取验证码.
        String code = map.get("code").toString();
        //3.从redis中获取保存的验证码
        Object codeInSession =redisTemplate.opsForValue().get("LOGIN_CODE_KEY"+phone) ;
        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if(codeInSession != null && codeInSession.equals(code)){
            //4.如果能够比对成功，说明登录成功
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            User user = this.getOne(queryWrapper);
            if(user == null){
                //5.判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                this.save(user);
            }
            //6.登录成功之后把user写入session
            session.setAttribute("user",user.getId());
            //7.如果用户登录成功就可以删除redis中的验证码
            redisTemplate.delete("LOGIN_CODE_KEY"+phone);
            return R.success(user);
        }
        return R.error("登录失败");

    }



}
