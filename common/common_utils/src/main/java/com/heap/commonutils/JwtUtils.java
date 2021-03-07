package com.heap.commonutils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Condor
 * @since 2021/02/06
 */
public class JwtUtils {

    //设置token过期时间
    public static final long EXPIRE = 1000 * 60 * 60 * 24;

    //密钥，随便写的
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    //生成token字符串
    public static String getJwtToken(String id, String nickname){
        String JwtToken = Jwts.builder()
                //设置jwt头信息
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                //设置字符串过期时间
                .setSubject("coin-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))  //当前时间+过期时间值

                //设置token主体部分，存储用户信息
                .claim("id", id)
                .claim("nickname", nickname)

                //签名哈希，根据密钥和方式进行编码
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    //判断token是否存在与有效
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) {
            return false;
        }

        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //判断token是否存在与有效，参数与上面不同，通过request在header里面将字符串得到，再做验证
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) {
                return false;
            }
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    //根据token获取会员id
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) {
            return "";
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }
}
