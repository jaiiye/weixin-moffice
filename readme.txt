2014.11.17 
=============================================================
1.修改mock相关类，使其满足对getRequestURL，getReader等函数的调用；
     可满足微信借口的单元测试
2.加密算法有问题（加密后再解密，和原名文，不一致）
     自己增加了方法，进行修正，微信自己的加密方法仍然不知道；
3.
	 
2014-11-17 17:05:50
[INFO]-[Thread: http-80-9]-[com.jfinal.weixin.demo.CorpController.index()]: 接收消息:<xml><ToUserName><![CDATA[wxb21adacab9c87404]]></ToUserName>
<Encrypt><![CDATA[NQ4iqwUCpkl+/1sU4wF6d37gMLJyLeYiHGRpZB/cAqvET7SIVDOAGzO/bnd5zYHZWLgvOm8BsA6m1Vaq3fL6aBQRQB+EaZgOlEAbX2ARJyR8tnFqOTLqF+4MO0WKJ1IvYGWCw8fROax5AHd5v3iGmIszjqKrRXlL7Oeq547X++l94wUtM+BiZgSeAjUsOmUyOi7mA5DG+LXSoweLVqCIvat53mkoNZdq7uSHtqUrqogskTg8aAc/lW2i/YIluYQKu+RIKbx6rGx/moms+LWMqi+cW8d0WJa3/aZlvNHZMukHV8P3TFNlPa3rwPTvg8zGO4hcN1lGOPLLs40o76ykSmnbhYxjKZe7P5uuUMc7bkAsARSaIfefp+bOTc3Wu06d0HdXvrKga5n4NhOVxBQ8H2j5lRqmYIZ4ZLJwY1MQU1KH7EEj1uOgwJfJMbfKNaQYzGDMuXcRP9WkukzNteoq52U9kBBODNYooAR/eR2Ry06mavuwHL0/ChQWcw6XqRmO1TXyFvYFpf08NxJa3x4QAFlmGRXvbL2cjoUEISBJzQO7Nxh/G/KKsHaCgRDmbFDDAQHM5kQ6o68L34oriGTk3EX4fhX+0bakItJwyvQ6tXtexPUpJkdJExxEm5dGel2QTqw07afMhH5AqjCeAvkcZkItldnkMYuUlnvJzl91npfPki+wS+FdOifP7/Wpa7uKMI78p+p/Ky4znj+LAb+e4ROC8P6yT5ZYZrrXK47GjDgtvadIFewTEQ6W3AJJNif+]]></Encrypt>
<AgentID><![CDATA[10]]></AgentID>
</xml>


2014-11-17 17:05:50
[INFO]-[Thread: http-80-9]-[com.jfinal.weixin.demo.CorpController.getInMsg()]: 脱密消息:<xml><ToUserName><![CDATA[wxb21adacab9c87404]]></ToUserName>
<FromUserName><![CDATA[15991890112]]></FromUserName>
<CreateTime>1416215152</CreateTime>
<MsgType><![CDATA[event]]></MsgType>
<Event><![CDATA[location_select]]></Event>
<EventKey><![CDATA[rselfmenu_2_0]]></EventKey>
<SendLocationInfo><Location_X><![CDATA[34]]></Location_X>
<Location_Y><![CDATA[109]]></Location_Y>
<Scale><![CDATA[15]]></Scale>
<Label><![CDATA[]]></Label>
<Poiname><![CDATA[]]></Poiname>
</SendLocationInfo>
<AgentID>10</AgentID>
</xml>
	 