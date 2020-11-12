<template>
  <b-container class="mt-5 mb-5">
    <!-- Зачем нужен сервис -->
    <b-container class="mt-3 mb-5" fluid>
      <b-row  align-h="center">
        <h1 class="text-center font-weight-normal"> Зачем нужен сервис? </h1>
      </b-row>
      <b-row  align-h="center" class="mt-5">
        <h2 class="text-center font-weight-normal"> Сервис дает возможность произвести нормализацию адресов в базах данных. </h2>
      </b-row>
      <b-row  align-h="center" class="mt-5">
        <h2 class="text-center font-weight-normal"> Всего <b>четыре</b> простых шага </h2>
      </b-row>
    </b-container>
    <!-- Загрузка файла -->
    <b-container class="mt-5 mb-5">
      <b-row  align-h="center">
        <h2 class="text-center font-weight-normal">
          <b-link href="#/handle" class="card-link">Загрузите</b-link>
           подготовленыый файл для обработки в формате .xlsx или .csv
        </h2>
      </b-row>
      <b-row  align-h="center" class="mt-5 mb-5">
        <b-img src="@/assets/examples/file.png" fluid rounded alt="File example"></b-img>
      </b-row>
    </b-container>
    <!-- Просмотр результатов -->
    <b-container class="mt-5 mb-5">
      <b-row  align-h="center" class="mt-5 mb-3">
        <h2 class="text-center font-weight-normal">
          Посмотрите результат обработки адресов
        </h2>
      </b-row>
      <b-row  align-h="center" class="mt-5 mb-5">
        <b-img src="@/assets/examples/preview.png" fluid rounded alt="Base preview"></b-img>
      </b-row>
    </b-container>
    <!-- Исправление ошибок -->
    <b-container class="mt-5 mb-5">
      <b-row  align-h="center" class="mt-5 mb-3">
        <h2 class="text-center font-weight-normal">
          При необходимости отредактируйте нераспознанные адреса
        </h2>
      </b-row>
      <b-row  align-h="center" class="mt-5 mb-5">
        <b-img src="@/assets/examples/edit.png" fluid rounded alt="Base preview"></b-img>
      </b-row>
    </b-container>
    <!-- Выгрузка -->
    <b-container class="mt-5 mb-5">
      <b-row  align-h="center" class="mt-5 mb-3">
        <h2 class="text-center font-weight-normal">
          Загрузите обработанную базу адресов
        </h2>
      </b-row>
      <b-row  align-h="center" class="mt-5 mb-5">
        <b-img src="@/assets/examples/download.png" fluid rounded alt="Base preview"></b-img>
      </b-row>
    </b-container>
    <!-- Начать -->
    <b-container class="mt-5 mb-5 pl-5 pr-5">
      <b-row  align-h="center" class="mt-5 mb-3">
        <b-button variant="primary" class="w-50" to="handle" size="lg">Начать</b-button>
      </b-row>
    </b-container>
  </b-container>
</template>
