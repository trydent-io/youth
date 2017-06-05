<template>
  <div class="ui icon floating message" :class="{ negative: isNegative, positive: isPositive, info: isInfo }">
    <i class="icon" :class="{ frown: isNegative, smile: isPositive, 'info circle': isInfo }"></i>
    <div class="content">
      <div class="header">
        {{title}}
      </div>
      <p>{{message}}</p>
    </div>
  </div>
</template>

<script>
  export default {
    props: ['title', 'message', 'type'],
    computed: {
      isNegative () { return this.type === 'negative' || this.type === 'error' },
      isPositive () { return this.type === 'positive' || this.type === 'success' },
      isInfo () { return this.type === 'info' }
    }
  }
</script>
