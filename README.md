# EmailWarningDemo
该demo为系统发生异常时，通过邮件发送到指定邮箱通知开发人员调试

参考资料：https://blog.csdn.net/sinat_34454743/article/details/98622409

在demo1.class 类中，模拟了各种请求方式发生异常后的效果
注意：其中在requestBody中。因HttpServletRequest 流只允许读取一次，即在requestBody中已经读取过了，因此会导致一个问题：
在全局异常拦截时会读取不到传参信息。因此通过自定义拦截器+重写请求流可重复读取策略来规避
参考：
https://blog.csdn.net/weixin_40773848/article/details/124328226
<br>
https://blog.csdn.net/ghw105745/article/details/107062103/

![图片](https://user-images.githubusercontent.com/54977975/192240298-77821fac-1db1-42a5-82be-59ef8bccf1be.png)
![图片](https://user-images.githubusercontent.com/54977975/192240428-39197ab4-37d8-461b-800f-ac32a2f6480a.png)
