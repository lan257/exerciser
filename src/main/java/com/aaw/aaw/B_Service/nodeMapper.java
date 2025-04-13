package com.aaw.aaw.B_Service;


import com.aaw.aaw.O_solidObjects.mindMap.node;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
@Repository
public interface nodeMapper extends BaseMapper<node> {
}
