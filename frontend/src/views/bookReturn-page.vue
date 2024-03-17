<template>
  <header>
    <button @click="onSubmit" :disabled="books.length === 0" :style="books.length === 0 ? 'background-color: #ccc; color: #666; cursor: not-allowed;' : 'background-color: #4caf50; cursor: pointer;'">還書</button>
    <button @click="toRecord">借閱紀錄</button>
    <button @click="toHome">回到總覽</button>
  </header>
  <section>
    <div class="centered-text" v-if="books.length === 0">
      <p>目前沒有借閱中的書籍</p>
    </div>
    <form @submit="onSubmit">
      <article class="each_book">
        <div class="book_info" v-for="book in books" :key="book.recordId">
          <div>
            <label>書名：</label><span>{{ book.name }}</span>
            <br />
            <label>作者：</label><span>{{ book.author }}</span>
            <br />
            <label>ISBN：</label><span>{{ book.isbn }}</span>
            <br />
            <label>書籍狀態：</label><span>{{ book.status }}</span>
          </div>
          <input type="checkbox" v-model="selectedBooks" :value="book.recordId" />
        </div>
      </article>
      <button type="submit" :disabled="books.length === 0" :style="books.length === 0 ? 'background-color: #ccc; color: #666; cursor: not-allowed;' : 'background-color: #4caf50; cursor: pointer;'" style="margin-right: 10px">還書</button>
      <button @click="toRecord" style="margin-right: 10px">借閱紀錄</button>
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
    const filteredData = data.filter(book => book.returnTime === null);
    books.value = filteredData;
  });
});

const toRecord = () => {
  router.push("/record");
};

const toHome = () => {
  router.push("/");
};

const selectedBooks = ref([]);

const onSubmit = (event) => {
  event.preventDefault();

  fetch(`http://localhost:8080/book/return`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      userId: localStorage.getItem("userId"),
      recordId: selectedBooks.value
    }),
  })
    .then(() => {
      alert("還書成功");
      router.push("/");
    })
    .catch(() => {
      alert("還書失敗");
    });
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
.centered-text {
  text-align: center;
}
</style>
