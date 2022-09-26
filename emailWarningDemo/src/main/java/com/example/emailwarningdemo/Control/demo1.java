package com.example.emailwarningdemo.Control;

import com.example.emailwarningdemo.Utils.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/tt1")
public class demo1 {

    @GetMapping("test1")
    public R tt1(@RequestParam("a") int a,@RequestParam("b") int b){
        int c = a/b;
        return R.ok();
    }

    @PostMapping("test2")
    public R tt2(@RequestBody Map<String, Object> param){
        //RequestBody在读取后httpRequest就无法读取到body
        //参考文章：
        //https://blog.csdn.net/weixin_40773848/article/details/124328226
        //https://blog.csdn.net/ghw105745/article/details/107062103/
        //文章2只需要参考拦截器配置类即可。
        int param1 = (int) param.get("dd");
        int a = 3 / param1;
        return R.ok();
    }

    @GetMapping("test3")
    public R tt3(){
        int c = 3/0;
        return R.ok();
    }

    @PostMapping("test4")
    public R test4(HttpServletRequest request){
        int c = 3/0;
        return R.ok();
    }

    @PostMapping("test5")
    public R test5(@RequestParam(value = "file") @RequestPart MultipartFile file){
        int b = 3/0;
        return R.ok();
    }
}
