<template>
	<div class="login-layout">
		<!-- <div class="login-header">
			<logo class="login-logo"/>
			<hr/>
		</div> -->
		<b-form @submit="onSubmit" class="login-form">
			<b-form-group id="input-login" label="Логин" label-for="login">
				<b-form-input squared
					id="login"
					v-model="form.login"
					type="text"
					required
				></b-form-input>
			</b-form-group>

			<b-form-group id="input-password" label="Пароль" label-for="password">
				<b-form-input squared
					id="password"
					v-model="form.password"
					type="password"
					required
				></b-form-input>
			</b-form-group>

			<div class="login-buttons">
				<b-button type="submit" squared variant="outline-primary">Войти</b-button>
				<logoEsia class="logo-esia"/>
			</div>
		</b-form>

	</div>
</template>

<script>
import logo from '@/components/logo'
import logoEsia from '@/components/logoEsia'
export default {
  data () {
    return {
			form: {
				login: '',
				password: '',
				token: '',
				key: ''
			}
    }
  },
  methods: {
		onSubmit (e) {
			e.preventDefault()
			console.log(this.form)
			this.$bs.doLogin(this.form.login, this.form.password)
				.then(data => {
					console.log(data)
					if (data) {
						// this.$set(this, '$loggedIn', true)
						// this.$router.push('/upload')
					}
				})
		}
	},
	components: {
		logo,
		logoEsia
	}
}
</script>

<style lang="stylus">
.login-layout
	display flex
	flex-direction column
	width 100%
	height 100vh
	justify-content center
	align-items center
	hr
		width 100%

.login-form
	// margin-bottom auto

.login-buttons
	display flex
	justify-content space-between

.logo-esia
	width 38px
	height 38px

.login-header
	display flex
	flex-direction column
	width 100%
	padding 1rem 0
	margin-bottom auto
	.login-logo
		margin auto

</style>
