package com.aaw.aaw.H_tool;
import com.aaw.aaw.O_solidObjects.user;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class jwt {
    public jwt() {
    }

    public String setJwt(user user){

        Map<String,Object> claims =new HashMap<>();
        claims.put("userId",user.getUid());
        claims.put("nickName",user.getNickname());
        claims.put("userType",user.getType());
        //System.out.println(jwt);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"androidAndWeb")//安卓web跨平台，2024.1.11
                .setClaims(claims)//内存数据
                .setExpiration(new Date(System.currentTimeMillis()+3600*1000))
                .compact();
    }
    public user getJwt(String jwt){
        Claims claims= Jwts.parser()
                .setSigningKey("androidAndWeb")//安卓web跨平台，2024.1.11
                .parseClaimsJws(jwt)
                .getBody();

        user user=new user();
        user.setUid(claims.get("userId", Integer.class));
        user.setNickname(claims.get("nickName", String.class));
        user.setType(claims.get("userType", Integer.class));
        return user;
    }

}
