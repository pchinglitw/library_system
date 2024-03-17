<template>
  <section>
    <form @submit="onSubmit">
      <h3>登入會員</h3>
      <label htmlFor="phone">手機號碼</label>
      <br />
      <input name="phone" />
      <br />
      <label htmlFor="password">密碼</label>
      <br />
      <input name="password" type="password" />
      <br />
      <br />
      <button type="submit">登入</button>
      <br />
      <span> 沒有帳號？</span>
      <router-link to="/register"> 現在註冊 </router-link>
    </form>
  </section>
</template>

<script setup>
import { RouterLink, useRouter } from "vue-router";

const router = useRouter();

const onSubmit = async (event) => {
  event.preventDefault();

  const data = {
    phone: event.target[0].value,
    password: event.target[1].value,
  };

  try {
    const res = await (
      await fetch(`http://localhost:8080/user/login`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          phoneNum: data.phone,
          password: data.password,
        }),
      })
    ).json();

    localStorage.setItem("userId", res.userId);
    router.push("/");
  } catch (error) {
    alert("發生錯誤，請稍後再試");
    throw error;
  }
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
