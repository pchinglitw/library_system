<template>
  <section>
    <form @submit="onSubmit">
      <h3>注冊會員</h3>
      <label htmlFor="phone">手機號碼</label>
      <br />
      <input name="phone" />
      <br />
      <label htmlFor="password">密碼</label>
      <br />
      <input name="password" type="password" />
      <br />
      <label htmlFor="password">確定密碼</label>
      <br />
      <input name="confirm_password" type="password" />
      <br />
      <label htmlFor="password">使用者名稱</label>
      <br />
      <input name="userName" />
      <br />
      <br />
      <button type="submit">註冊</button>
      <br />
      <span> 已有帳號？</span>
      <router-link to="/login"> 現在登入 </router-link>
    </form>
  </section>
</template>

<script setup>
import { RouterLink, useRouter } from "vue-router";

const router = useRouter();

const onSubmit = (event) => {
  event.preventDefault();

  const data = {
    phone: event.target[0].value,
    password: event.target[1].value,
    confirm_password: event.target[2].value,
    name: event.target[3].value,
  };
  if (data.password !== data.confirm_password) alert("密碼與確認密碼不相同");

  fetch(`http://localhost:8080/user/register`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      phoneNum: data.phone,
      password: data.password,
      name: data.name
    }),
  })
    .then(() => {
      alert("註冊成功");
      router.push("/login");
    })
    .catch((error) => {
      alert("發生錯誤，請稍後再試");
    });
};
</script>

<style scoped>
section {
  width: 100%;
  height: 100%;
  display: grid;
  place-items: center;
}
a {
  cursor: pointer;
  color: blue;
  text-underline-offset: 2px;
  text-decoration: underline;
}
</style>
