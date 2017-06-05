import Vue from 'vue'
import $ from 'jquery'

const positions = { center: 'center', top: 'top', bottom: 'bottom' }

Vue.directive('uthTooltip', {
  inserted (el, binding) {
    const position = binding.arg === undefined || positions[binding.arg] === undefined ? 'center' : binding.arg
    const align = binding.modifiers.right !== undefined ? 'right' : 'left'

    console.log(`Position: ${position}; Align: ${align}`)

    $(el).wrap(`<span data-tooltip="${binding.value}" data-inverted="" data-position="${align} ${position}"></span>`)
  }
})

Vue.directive('uthDropdown', {
  inserted (el, binding) {
    $(el).dropdown(binding.arg)
  }
})

Vue.directive('uthCombobox', {
  inserted (el, binding) {
    binding.value.options['action'] = 'combo'
    for (let p in binding.value) {
      if (p !== 'options') binding.value.options[p] = binding.value[p]
    }

    $(el).dropdown(binding.value.options)
  }
})
