(this.webpackJsonpfrontend=this.webpackJsonpfrontend||[]).push([[0],[,,,,,,,,,,function(e,a,t){},,function(e,a,t){},,,function(e,a,t){e.exports=t(32)},,,,,,,,function(e,a,t){},,,,,function(e,a,t){},function(e,a,t){},function(e,a,t){},function(e,a,t){},function(e,a,t){"use strict";t.r(a);var n=t(0),l=t.n(n),r=t(13),c=t.n(r),m=t(6),s=(t(23),t(12),t(3)),i=t(33);var u=new class{saveUser(e){return i.a.post("/SignUp/signup",e,{headers:{"Content-Type":"multipart/form-data"}})}loginUser(e){return i.a.post("/Login",e,{headers:{"Content-Type":"multipart/form-data"}})}static async getUser(e,a){console.log(e);return(await i.a.post(a,{params:{id:e}})).data}};function o(){const e=Object(s.o)(),[a,t]=Object(n.useState)({email:"",password:""}),r=e=>{t({...a,[e.target.id]:e.target.value})};return l.a.createElement("div",{className:"login"},l.a.createElement("h1",null,"Welcome"),l.a.createElement("form",{onSubmit:async t=>{t.preventDefault();const n=new FormData;n.append("email",a.email),n.append("password",a.password);try{const a=await u.loginUser(n);localStorage.setItem("userID",a.data),1===a.data?e("/AdminPage"):e("/HomePage")}catch(l){console.error("There was an error registering the user:",l),alert("There was an error registering the user.")}}},l.a.createElement("label",null,"E-mail:"),l.a.createElement("br",null),l.a.createElement("input",{type:"email",required:!0,value:a.email,onChange:r,id:"email"}),l.a.createElement("br",null),l.a.createElement("label",null,"Password:"),l.a.createElement("br",null),l.a.createElement("input",{type:"password",required:!0,value:a.password,onChange:r,id:"password"}),l.a.createElement("br",null),l.a.createElement("input",{className:"LoginButton",type:"submit",value:"Login"}),l.a.createElement("br",null),l.a.createElement("div",{className:"signup"},"Create an account:",l.a.createElement("input",{className:"WelcomeButton",type:"button",value:"Sign Up",onClick:()=>{e("/SignUp")}}))))}var p=function(){return l.a.createElement("div",null,l.a.createElement(o,null))};t(28);var E=function(){const e={first_name:"",last_name:"",email:"",password:"",repeat_password:"",phone:"",profile_picture:null,cv:null},[a,t]=Object(n.useState)(e),r=Object(s.o)(),c=e=>{t({...a,[e.target.id]:e.target.value})},m=e=>{const{id:a,files:n}=e.target;t(e=>({...e,[a]:n[0]}))};return l.a.createElement("div",null,l.a.createElement("form",{className:"signUp",onSubmit:async e=>{if(e.preventDefault(),a.password!==a.repeat_password)return void alert("Passwords don't match");const t=new FormData;t.append("email",a.email),t.append("firstName",a.first_name),t.append("lastName",a.last_name),t.append("password",a.password),t.append("phoneNumber",a.phone),t.append("profilePicture",a.profile_picture),t.append("resume",a.cv);try{const e=await u.saveUser(t);alert("User registered successfully"),console.log(e.data),r(-1)}catch(n){console.error("There was an error registering the user:",n),alert("There was an error registering the user.")}}},l.a.createElement("label",null,"E-mail:"),l.a.createElement("br",null),l.a.createElement("input",{type:"email",required:!0,value:a.email,onChange:c,id:"email"})," ",l.a.createElement("br",null),l.a.createElement("label",null,"First Name: "),l.a.createElement("br",null),l.a.createElement("input",{type:"text",required:!0,value:a.first_name,onChange:c,id:"first_name"}),l.a.createElement("br",null),l.a.createElement("label",null,"Last Name:"),l.a.createElement("br",null),l.a.createElement("input",{type:"text",required:!0,onChange:c,value:a.last_name,id:"last_name"}),l.a.createElement("br",null),l.a.createElement("label",null,"Password:"),l.a.createElement("br",null),l.a.createElement("input",{type:"password",required:!0,onChange:c,value:a.password,id:"password"}),l.a.createElement("br",null),l.a.createElement("label",null,"Repeat Password: "),l.a.createElement("br",null),l.a.createElement("input",{type:"password",required:!0,onChange:c,value:a.repeat_password,id:"repeat_password"}),l.a.createElement("br",null),l.a.createElement("label",null,"Phone Number: "),l.a.createElement("br",null),l.a.createElement("input",{type:"tel",onChange:c,value:a.phone,id:"phone"}),l.a.createElement("br",null),l.a.createElement("div",{className:"profile"},l.a.createElement("label",null,"Profile Picture:"),l.a.createElement("input",{type:"file",className:"button",onChange:m,id:"profile_picture"}),l.a.createElement("br",null)),l.a.createElement("div",{className:"profile"},l.a.createElement("label",null,"CV: "),l.a.createElement("input",{type:"file",className:"button",onChange:m,id:"cv"}),l.a.createElement("br",null)),l.a.createElement("div",{className:"submit"},l.a.createElement("input",{type:"reset",value:"Reset",className:"button",onClick:()=>{t(e),document.getElementById("profile_picture").value="",document.getElementById("cv").value=""}}),l.a.createElement("input",{type:"submit",value:"Sign Up",className:"submit_button"}))))};t(29);function d(){return l.a.createElement("header",null,l.a.createElement("nav",{className:"navigation"},l.a.createElement("a",{href:"/HomePage",className:"link"},"HomePage "),l.a.createElement("a",{href:"/Network",className:"link"},"Network "),l.a.createElement("a",{href:"/Jobs",className:"link"},"Jobs "),l.a.createElement("a",{href:"/Messages",className:"link"},"Messages "),l.a.createElement("a",{href:"/Notifications",className:"link"},"Notifications "),l.a.createElement("a",{href:"/Profile",className:"link"},"Profile "),l.a.createElement("a",{href:"/Settings",className:"link"},"Settings ")))}var g=function(){return l.a.createElement("div",null,l.a.createElement(d,null))};t(30);var h=new class{getUsers(){return i.a.get("/AdminPage/").then(e=>e.data)}};function b(){const[e,a]=Object(n.useState)([]);let[t,r]=Object(n.useState)([]);Object(n.useEffect)(()=>{(async()=>{try{const e=(await h.getUsers()).filter(e=>!0!==e.admin);a(e)}catch(e){console.error("There was an error getting the user list",e)}})()},[]);return l.a.createElement("div",null,l.a.createElement("table",{className:"userlist"},l.a.createElement("thead",{className:"header"},l.a.createElement("tr",null,l.a.createElement("th",{className:"select"}," ",l.a.createElement("input",{type:"checkbox",name:"selectall",onChange:()=>{t.length!==e.length?r(e.map(e=>e.id)):r([])},checked:t.length===e.length}),"   "),l.a.createElement("th",null," Name "),l.a.createElement("th",null," E-mail "),l.a.createElement("th",null," Profile Page"))),l.a.createElement("tbody",null,e.map(e=>l.a.createElement("tr",{key:e.id},l.a.createElement("td",{className:"select"}," ",l.a.createElement("input",{type:"checkbox",name:e.id,checked:t.includes(e.id),onChange:()=>{var a;a=e.id,r(e=>e.includes(a)?e.filter(e=>e!==a):[...e,a])}}),"  "),l.a.createElement("td",null,e.firstName," ",e.lastName),l.a.createElement("td",null," ",e.email),l.a.createElement("td",null," ",l.a.createElement("a",{href:"/profile/".concat(e.firstName).concat(e.lastName)}," Visit Profile ")))))),l.a.createElement("input",{type:"button",value:"Export in JSON ",className:"button",onClick:()=>{alert("JSON")}}))}var f=function(){return l.a.createElement("div",null,l.a.createElement(b,null))};t(31);var v=function(){};t(10);var w=function(){return l.a.createElement("div",{className:"table"},l.a.createElement("h1",null,l.a.createElement("b",null,"Settings")),l.a.createElement("br",null),l.a.createElement("nav",null,l.a.createElement("a",{href:"/NewEmail"},"Change e-mail"),l.a.createElement("br",null),l.a.createElement("br",null),l.a.createElement("a",{href:"/NewPassword"},"Change password")))};var N=function(){const e=localStorage.getItem("userID"),[a,t]=Object(n.useState)({email:""});return Object(n.useEffect)(()=>{(async()=>{try{const a=await u.getUser(e,"/NewEmail");t(a)}catch(a){throw Error("DID NOT GET USER DATA")}})()},[e]),l.a.createElement("div",{className:"email-table"},l.a.createElement("h1",null,"Change Email"),l.a.createElement("p",null,"Your current email is: ",a.email),l.a.createElement("br",null),l.a.createElement("form",null,l.a.createElement("label",null,"New email: "),l.a.createElement("input",{type:"email"}),l.a.createElement("br",null),l.a.createElement("br",null),l.a.createElement("input",{type:"submit",value:"Change"})))};var y=function(){return l.a.createElement("div",null,l.a.createElement("div",{className:"password-table"},l.a.createElement("h1",null,"Change Password"),l.a.createElement("br",null),l.a.createElement("form",null,l.a.createElement("label",null,"New Password: "),l.a.createElement("input",{type:"password"}),l.a.createElement("br",null),l.a.createElement("br",null),l.a.createElement("label",null,"Repeat New Password: "),l.a.createElement("input",{type:"password"}),l.a.createElement("br",null),l.a.createElement("br",null),l.a.createElement("input",{type:"submit",value:"Change"}))))};var C=()=>l.a.createElement(s.c,null,l.a.createElement(s.a,{exact:!0,path:"/",Component:p}),l.a.createElement(s.a,{exact:!0,path:"/SignUp",Component:E}),l.a.createElement(s.a,{exact:!0,path:"/AdminPage",Component:f}),l.a.createElement(s.a,{exact:!0,path:"/HomePage",Component:g}),l.a.createElement(s.a,{exact:!0,path:"/Profile",Component:v}),l.a.createElement(s.a,{exact:!0,path:"/Settings",Component:w}),l.a.createElement(s.a,{exact:!0,path:"/NewEmail",Component:N}),l.a.createElement(s.a,{exact:!0,path:"/NewPassword",Component:y}));var P=function(){return l.a.createElement("div",{className:"App"},l.a.createElement(C,null))};var S=e=>{e&&e instanceof Function&&t.e(3).then(t.bind(null,34)).then(a=>{let{getCLS:t,getFID:n,getFCP:l,getLCP:r,getTTFB:c}=a;t(e),n(e),l(e),r(e),c(e)})};c.a.createRoot(document.getElementById("root")).render(l.a.createElement(l.a.StrictMode,null,l.a.createElement(m.a,null,l.a.createElement(P,null)))),S()}],[[15,1,2]]]);
//# sourceMappingURL=main.494eedc3.chunk.js.map