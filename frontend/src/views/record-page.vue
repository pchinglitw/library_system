<template>
  <header>
    <button @click="toReturn">我要還書</button>
    <button @click="toHome">回到總覽</button>
  </header>
  <section>
    <form @submit="onSubmit">
      <article>
        <div class="book_info" v-for="book in books" :key="book.recordId">
          <div>
            <label>書名：</label><span>{{ book.bookName }}</span>
            <br />
            <label>作者：</label><span>{{ book.author }}</span>
            <br />
            <label>ISBN：</label><span>{{ book.isbn }}</span>
            <br />
            <label>書籍狀態：</label><span>{{ book.status }}</span>
            <br />
            <label>借出時間：</label><span>{{ book.borrowingTime }}</span>
            <br />
            <label>歸還日期：</label><span>{{ book.returnTime }}</span>
          </div>
        </div>
      </article>
      <button @click="toReturn" style="margin-right: 10px">我要還書</button>
      <button @click="toHome">回到總覽</button>
    </form>
  </section>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const books = ref([]);

onMounted(() => {
  fetch(`http://localhost:8080/borrowingRecord/all`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      userId: localStorage.getItem("userId"),
    }),
  }).then(async (res) => {
    const data = await res.json();
    books.value = data;
  });
});

const toReturn = () => {
  router.push("/bookReturn");
};

const toHome = () => {
  router.push("/");
};

</script>

<style scoped>
header {
  padding: 10px;
  padding-right: 30px;
  display: flex;
  justify-content: end;
  gap: 8px;
}
section {
  width: 100%;
}
form {
  padding: 10px;
}
article {
  display: flex;
  gap: 10px;
  flex-direction: column;
  align-items: center;
  flex-wrap: wrap;
}
a {
  cursor: pointer;
  color: blue;
  text-underline-offset: 2px;
  text-decoration: underline;
}
input {
  position: absolute;
  cursor: pointer;
  height: 15px;
  width: 15px;
  top: 10px;
  right: 10px;
}
button {
  margin-top: 8px;
  cursor: pointer;
  background-color: #4caf50;
  color: white;
  border: none;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  border-radius: 4px;
  transition-duration: 0.4s;
}
.book_info {
  position: relative;
  display: flex;
  gap: 50px;
  padding: 10px;
  border-radius: 8px;
  width: 60%;
  height: fit-content;
  border: 1px solid black;
}
</style>
