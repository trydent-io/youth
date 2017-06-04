import Vue from 'vue'
import $ from 'jquery'

Vue.directive('uthTooltip', {
  inserted (el, binding) {
    let position = binding.modifiers.right === undefined ? '' : 'right'
    position = binding.modifiers.left === undefined ? '' : 'left'
    position = position === '' ? 'center' : position
    console.log(binding)

    $(el).wrap(`<span data-tooltip="${binding.expression}" data-inverted="" data-position="${binding.arg} ${position}"></span>`)
  }
})
