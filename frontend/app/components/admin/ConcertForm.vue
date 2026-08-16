<script setup lang="ts">
import { ref, computed, reactive } from "vue";
import { FetchError } from "ofetch";

const successMessage = ref("");
const errorMessage = ref("");

const form = reactive({
  venue: "",
  city: "",
  date: "",
  ticketPrice: "",
  ticketLink: "",
});

const errors = reactive({
  venue: "",
  city: "",
  date: "",
  ticketPrice: "",
  ticketLink: "",
});

const validate = () => {
  errors.venue = "";
  errors.city = "";
  errors.date = "";
  errors.ticketPrice = "";
  errors.ticketLink = "";

  const price = Number(form.ticketPrice);

  if (!form.venue.trim()) {
    errors.venue = "Venue cannot be empty";
  }

  if (!form.city.trim()) {
    errors.city = "City cannot be empty";
  }

  if (!form.date) {
    errors.date = "Date must be set";
  }

  if (form.ticketPrice === "" || isNaN(price) || price < 0) {
    errors.ticketPrice = "Price cannot be negative or empty";
  }

  if (!form.ticketLink.trim()) {
    errors.ticketLink = "Ticket link cannot be empty";
  }

  return Object.values(errors).every((e) => !e);
};

async function handleSubmit() {

    errorMessage.value = "";
    successMessage.value = "";

  const isValid = validate();

  if (!isValid) {
    return;
  }
  try {
    const response = await $fetch("http://localhost:8080/api/concerts", {
      method: "POST",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        ...form,
        ticketPrice: Number(form.ticketPrice),
      }),
    });

    console.log("Success:", response);

    form.city = "";
    form.venue = "";
    form.date = "";
    form.ticketPrice = "";
    form.ticketLink = "";
    successMessage.value = "Concert created!";
  } catch (error) {
    if (error instanceof FetchError) {
      if (error.statusCode === 401) {
        errorMessage.value = "Your session has expired. Please log in again.";
      } else {
        errorMessage.value =
          error.data?.message ||
          "Could not save the concert. Check the fields and try again.";
      }
      console.error("Error: ", error);
    } else {
      errorMessage.value = "Something went wrong";
      console.error("Error: ", error);
    }
  }
}
</script>

<template>
  <h1>Add a concert:</h1>

  <form
    class="flex flex-col items-center text-black font-bold"
    @submit.prevent="handleSubmit"
  >
    City: <input type="text" placeholder="City" v-model="form.city" />

    <div v-if="errors.city">{{ errors.city }}</div>

    <br />

    Venue: <input type="text" placeholder="Venue" v-model="form.venue" />

    <div v-if="errors.venue">{{ errors.venue }}</div>

    <br />
    Date: <input type="datetime-local" v-model="form.date" />

    <div v-if="errors.date">{{ errors.date }}</div>

    <br />
    Ticket price:
    <input type="Number" placeholder="0" v-model="form.ticketPrice" />

    <div v-if="errors.ticketPrice">{{ errors.ticketPrice }}</div>

    <br />
    Ticket link:
    <input type="text" placeholder="example.com" v-model="form.ticketLink" />

    <div v-if="errors.ticketLink">{{ errors.ticketLink }}</div>
    <br />

    <BaseButton variant="primary">Create concert</BaseButton>

    <div v-if="successMessage">
      {{ successMessage }}
    </div>

    <div v-if="errorMessage">
      {{ errorMessage }}
    </div>
  </form>
</template>
