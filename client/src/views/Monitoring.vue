<template>
    <div class="content">
        <h1>This is an module selection page</h1>
    </div>
</template>

<script>
export default {
    name: 'Monitoring',
    async mounted() {
        if (this.$store.state.module === null) {
            this.$router.push('module-selection');
        }
    }
};
</script>