<template>
	<div class="fullscreen">

		<div
				class="dropzone"
				:class="{ 'draghover': dragHover,
									'ready': showDropZone }"
				@drop.prevent="handleDrop"
        @dragenter.stop="handleDragOver"
        @dragleave.stop="handleDragLeave"
        @dragover.prevent>

				<div class="dropzone-white">
					<div class="drop-border">
						<div class="drop-mark">Положите файл сюда</div>
					</div>

				</div>
				<div class="dropzone-blue">
					<div class="drop-description">
						<h4><b-icon-file-earmark-plus/> Загрузите файл </h4>
						<div class="drop-devider"><span/> <p>или</p> <span/></div>
						<b-form-input
							size="lg"
							class="inp-theme-whiteblue"
							v-model="file.link"
							type="text"
							placeholder="Вставьте URL"
							@change="upload()"/>
					</div>
				</div>
		</div>
	</div>
</template>

<script>
export default {
  data () {
    return {
			showDropZone: false,
			dragHover: false,
			loading: false,
			file: {
				data: null,
				link: '',
        uploaded: 0,
        error: false,
        responseReceived: false,
        get showProgress () { return this.uploaded < 100 || !this.responseReceived }
      },
    }
	},
	created () {
		document.addEventListener('dragenter', this.handleDragEnter)
	},
	beforeDestroy () {
		document.removeEventListener('dragenter', this.handleDragEnter)
	},
  methods: {
		handleDragEnd (e) {
			this.showDropZone = false
		},
		handleDragEnter (e) {
			this.showDropZone = true
		},

		handleDragOver () {
      this.dragHover = true
    },
    handleDragLeave () {
			this.dragHover = false
		},
		
    handleDrop (e) {
			this.dragHover = false
			this.showDropZone = false
			const [data] = [...e.dataTransfer.files]
			this.file.data = data
      console.log(this.file)
		},
		upload () {
      this.loading = true
      this.$bs.uploadFileAsync(this.file)
        .then(() => {
					console.log('Файл загружен')
				})
        .then(() => {
          setTimeout(() => {
            this.loading = false
          }, 1000)
        })
    }
	},
	components: {
	}
}
</script>

<style lang="stylus" scoped>
.fullscreen
	display flex
	height 80vh

.dropzone
	overflow hidden
	margin auto
	display flex
	flex-wrap wrap
	width 900px
	height 350px
	background #FFFFFF
	box-shadow 0px 0px 30px rgba(0, 0, 0, 0.1)
	border-radius 10px
	transition box-shadow .15s ease
	&.draghover
		.dropzone-white
			.drop-border
				transform scale(1.1)
	&.ready
		box-shadow 0px 0px 30px #005fbc5c
	.dropzone-white
		display flex
		flex 1
		min-width 300px
		height 100%
		pointer-events none
		.drop-border
			padding 10px
			margin auto
			display flex
			min-width 200px
			min-height 150px
			width 70%
			height 50%
			border 2px dashed #0055a620
			border-radius 6px
			transition transform .2s ease
			.drop-mark
				display flex
				justify-content center
				align-items center
				width 100%
				height 100%
				background linear-gradient(263.82deg, #0064C5 4.04%, #004C9B 95.62%)
				border-radius 4px
				color white

	.dropzone-blue
		display flex
		flex 1
		min-width 300px
		background-color #005FBC
		box-shadow 0px 0px 30px rgba(0, 0, 0, 0.1)
		height 100%
		color white
		pointer-events none
		.drop-description
			margin auto
			display flex
			flex-direction column
			justify-content space-between
			align-items center
			min-width 200px
			min-height 150px
			width 70%
			height 50%
		.drop-devider
			display flex
			width 100%
			justify-content center
			align-items center
			span
				display block
				width 20%
				margin 0 20px
				height 1px
				background-color white
		input
			pointer-events all
</style>
