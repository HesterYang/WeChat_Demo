# WeChat_Demo
java开发微信公众号配置信息

#自定义菜单格式
{
    "button": [
        {
            "name": "继续阅读", 
            "sub_button": [
                {
                    "type": "click", 
                    "name": "签到", 
                    "key": "1"
                },
                {
                    "type": "view", 
                    "name": "继续阅读", 
                    "url": "xxx"
                }
            ]
        }, 
        {
            "name": "访问书城", 
            "sub_button": [
                {
                    "type": "view", 
                    "name": "书城首页", 
                    "url": "xxx"
                },
                {
                    "type": "view", 
                    "name": "热门推荐", 
                    "url": "xxx"
                }
            ]
        }, 
        {
            "name": "用户中心", 
            "sub_button": [
                {
                    "type": "view", 
                    "name": "个人中心", 
                    "url": "xxx"
                },
	              {
                    "type": "click", 
                    "name": "联系客服", 
                    "key": "2"
                }
            ]
        }
    ]
}

#获取自定义菜单中click属性的值
String content=map.get("EventKey");

#静默授权获取code的url
https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6e36e4d04f9f8755&redirect_uri=http%3A%2F%2Fmdgbook.com%2Fweixin_read%2Ff%2Fweixin%2Fshowuser?response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect
