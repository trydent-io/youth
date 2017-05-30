<!--suppress HtmlUnknownTag -->
<template>
  <md-card v-for="(p, index) in persons" :key="p.uuid" md-with-hover style="width: 100%">
    <md-card-media-cover md-text-scrim>
      <md-card-media md-ratio="16:9">
        <img src="/static/someone.jpg" alt="io">
      </md-card-media>
      <md-card-area>
        <md-card-header>
          <div class="md-title">{{ fullName(p) }}</div>
        </md-card-header>
        <md-card-actions>
          <md-button :class="{ 'md-icon-button': true, 'md-accent': p.liked === 1}" @click.native="liked(index)">
            <md-icon>favorite</md-icon>
          </md-button>
          <md-button class="md-icon-button md-primary" @click.native="openPerson(index)">
            <md-icon>editing</md-icon>
          </md-button>
          <md-button class="md-icon-button md-warn" @click.native="removing(index)">
            <md-icon>delete</md-icon>
          </md-button>
        </md-card-actions>
      </md-card-area>
    </md-card-media-cover>
  </md-card>
</template>
