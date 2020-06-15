<template>
    <div class="content">
        <h1>This is an calculation of use page</h1>
    </div>
</template>

<script>
export default {
    name: 'CalculationOfUse',
    async mounted() {
        if (this.$store.state.module === null) {
            this.$router.push('module-selection');
        }
    }
};
</script>