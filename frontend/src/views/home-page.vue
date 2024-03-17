<template>
  <header>
    <button @click="onLogout">我要還書</button>
    <button @click="onLogout">借閱紀錄</button>
    <button @click="onLogout">登出</button>
  </header>
  <section>
    <form @submit="onSubmit">
      <article class="each_book">
        <div class="book_info" v-for="book in books" :key="book.inventoryId">
          <div>
            <label>書名：</label><span>{{ book.name }}</span>
            <br />
            <label>作者：</label><span>{{ book.author }}</span>
            <br />
            <label>ISBN：</label><span>{{ book.isbn }}</span>
            <br />
            <label>書籍狀態：</label><span>{{ book.status }}</span>
          </div>
          <input type="checkbox" v-model="selectedBooks" :value="book.inventoryId" :disabled="book.status !== '在庫'" />
        </div>
      </article>
      <button type="submit">借書</button>
    </form>
  </section>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const books = ref([]);

const onRefresh = () => {
  fetch(`http://localhost:8080/book/all`, {
    method: "get",
    headers: {
      "Content-Type": "application/json",
    },
  }).then(async (res) => {
    const data = await res.json();
    books.value = data;
  });
};
onMounted(() => {
  onRefresh();
});

const onLogout = () => {
  fetch(`${process.env.VITE_BASE_URL}/logout`, {
    method: "post",
    "Content-Type": "application/json",
    body: {
      userId: localStorage.get("userId"),
    },
  }).then(() => {
    localStorage.clear();
    router.push("/login");
  });
};

const selectedBooks = ref([]);

const onSubmit = (event) => {
  event.preventDefault();

  fetch(`http://localhost:8080/book/borrow`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      userId: localStorage.getItem("userId"),
      inventoryId: selectedBooks.value,
    }),
  })
    .then(() => {
      alert("借書成功");
      onRefresh();
    })
    .catch(() => {
      alert("借書失敗");
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
</style>
