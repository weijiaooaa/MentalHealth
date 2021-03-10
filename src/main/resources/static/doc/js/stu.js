//学生登录校验
var app = new Vue({
    el: "#sign_in",
    data: {
        id: "",
        password: "",
        content: "",
        student: {},
    },
    methods: {
        submitForm: async function () {

            this.student = {}
//                用户名或密码为空
            if (this.id.split(" ").join("").length === 0
                || this.password.split(" ").join("").length === 0) {
                this.content = "用户名或密码不能为空"
                return false
            }
//                用户名或密码错误
            else {

                _this = this;

                axios({
                    method: "GET",
                    url: "stu/stuChecked",
                    data: {
                        id: this.id,
                        password: this.password
                    }
                }).then(function (res) {
                    _this.student = res.data;
//                        alert(_this.student)
                }).catch(function (error) {
                    console.log(error);
                });

                try{
                    await axios.get("/mhealth/stu/stuChecked?id="+this.id +"&password="+this.password).
                    then(res => {
                        //注意回调函数的this和vue的this会产生歧义
                        _this.student = res.data;
//                                        console.log("res.data.data",res.data.data) undefined
//                                        console.log("res.data",res.data) json
                    })
                }catch (err){
                    console.log(err)
                }

//                    alert("this.student" , this.student)
                console.log(this.student)
                if (!($.isEmptyObject(this.student))) {
                    this.content = ""
                    location.href = "/mhealth/stu/toHomePage?id=" + this.id
                }else {
                    this.content = "用户名或密码错误"
                }
            }
        }
    }
})

//学生注册
var app = new Vue({
    el: "#sign_up",
    data:{
        flag: 0,
        id: "",
        stuNumber: "",
        username: "",
        email: "",
        password1: "",
        password2: "",
        text1: "",
        text2: "",
        text3: "",
        text4: "",
        text5: "",
        student: {}
    },
    methods:{
        submitForm: async function () {

            this.flag = 0;
//                注意加this,表示vue与页面绑定的对象,以及如何判空
            if($.isEmptyObject(this.stuNumber)){
                this.text1 = "学生号不能为空"
            }else {
                this.flag++;
                this.text1 = "";
            }

            if($.isEmptyObject(this.username)){
                this.text2 = "用户名不能为空"
            }else{
                this.flag++;
                this.text2 = ""
            }

            if($.isEmptyObject(this.email)){
                this.text3 = "邮箱不能为空"
            }else{
                this.flag++;
                this.text3 = ""
            }

            if($.isEmptyObject(this.password1)){
                this.text4 = "密码不能为空"
            }else{
                this.flag++;
                this.text4 = ""
            }

            if(!(this.password1 === this.password2)){
                this.text5 = "密码输入不正确"
            }else{
                this.flag++
                this.text5=""
            }
            if(this.flag == 5){

                _this = this
                try{
                    await axios.get("/mhealth/stu/stuChecked1?stuNumber=" + this.stuNumber)
                        .then(res => {
                            _this.student = res.data
                        })
                }catch(err){
                    console.log(err);
                    alert("请求出错")
                }

                console.log(this.student)

                if(!$.isEmptyObject(this.student)){
                    alert($.isEmptyObject(this.student))
                    this.text1 = "该学生号已注册"
                }else{
                    // alert("注册成功")
//                      location.href="/mheal/stu/register?"
//                      post提交表单

                    let formdata = new FormData()
                    formdata.append("stuNumber",this.stuNumber)
                    formdata.append("name",this.username)
                    formdata.append("password",this.password1)
                    formdata.append("email",this.email)
                    for (var value of formdata.values()) {
                        console.log(value);
                    }

                    let config = {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }

                    //异步提交表单，即使有返回页面，也不会实现页面跳转
                    axios.post('/mhealth/stu/register',formdata,config).then(res => {
                        alert("提交表单")
                    })

                    location.href="/mhealth/stu";
                }
            }
        }
    }
})
