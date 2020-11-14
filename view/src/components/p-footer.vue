<template>
  <b-container class="mt-5 footer">
    <b-row align-v="center" align-h="between">
      <b-col cols="4">
        <b-link href="#/" class="text-theme-gray"> 2020 © АО Почта России </b-link>
      </b-col>
      <b-col cols="auto"> 
        <vk class="mr-2"/> <fb class="mr-2"/> <inst class="mr-3"/>
        <b-link href="#/" class="text-theme-gray mr-3"> Раскрытие информации </b-link>
        <b-link href="#/" class="text-theme-gray mr-3"> Вакансии </b-link>
        <b-link href="#/" class="text-theme-gray mr-3"> Пресс-центр </b-link>
        <b-link href="#/" class="text-theme-gray mr-3"> О компании </b-link>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import fb from '@/icons/fb'
import vk from '@/icons/vk'
import inst from '@/icons/inst'
export default {
	components: {
    fb,
    vk,
    inst
	}
}
</script>