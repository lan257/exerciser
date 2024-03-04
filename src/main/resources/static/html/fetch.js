//该函数将token（jwt令牌）加入请求头并发送至后端封装成一个函数
//deliver(url,data)传入url(请求发送目标，例如myVariablelogin")
// 和data(发送数据，如{name: '6512', email: '758439675'})
//返回后端返回数据
var vid="/aaw";
if(localStorage.getItem("yes")==="1"){
    var vid="http://localhost:8080/aaw";
}
function Fetch(url, data) {
    const token = localStorage.getItem('token');
    url=vid+url;
    console.log(url)
    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': '' + token,
        },
        body: JSON.stringify(data),
    })
        .then(response => {
            if (!response.ok) {
                // 如果响应的状态码不是2xx，那么就抛出一个错误
                return response.json().then(json => {
                    throw new Error(json.message);
                });
            }
            return response.json();
        })
        .catch(error => {
            return 0;
        });
}

async function getJwt(url) {
    let token = localStorage.getItem('token');
    console.log("getjwt执行："+url)

    if (!token) {
        localStorage.setItem('token', "");//保存jwt
        token = localStorage.getItem('token');
    }
    console.log(token)
        var a = await Fetch(url, {});//
        console.log("getjwt方法返回"+a)
        if(a.iu===0){alert(a.msg);return a}
        // if(a.iu===2){alert(a.msg); a.data.nikename="请登录"; return a}
        else {return a;}//登陆正常//
}