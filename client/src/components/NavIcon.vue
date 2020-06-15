<template>
    <img class="nav__icon" :src="image" alt="" aria-hidden="true">
</template>

<script>
export default {
    name: 'NavIcon',
    props: {
        src: {
            type: String,
            required: true
        }
    },
    computed: {
        image() {
            return require(`@/assets/${this.src}`);
        }
    }
};
</script>

<style lang="scss" scoped>
.nav__icon {
    width: 24px;
    height: 24px;
    display: block;
}
</style>