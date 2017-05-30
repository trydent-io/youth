<!--suppress HtmlUnknownTag -->
<template>
  <md-snackbar :md-position="'bottom left'" ref="snackbar" :md-duration="4000">
    <span>{{message}}</span>
    <md-button class="md-accent" md-theme="light-blue" @click.native="close">Close</md-button>
  </md-snackbar>
</template>

<script>
  export default {
    created () {
      const self = this

      this.$bus.$on('toastAlert', message => self.alert(message))
      this.$bus.$on('toastWarning', message => self.warning(message))
      this.$bus.$on('toastSuccess', message => self.success(message))
      this.$bus.$on('toastInfo', message => self.info(message))
    },
    data: () => ({
      message: 'Nothing to say'
    }),
    computed: {
      toast () { return this.$refs.snackbar }
    },
    methods: {
      open (message, options) {
        this.message = message
        this.toast.open()
      },
      close () {
        this.toast.close()
      },
      alert (message) { this.open(message, {}) },
      warning (message) { this.open(message, {}) },
      success (message) { this.open(message, {}) },
      info (message) { this.open(message, {}) }
    }
  }
</script>
