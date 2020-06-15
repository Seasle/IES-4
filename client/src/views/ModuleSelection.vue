<template>
    <div class="content">
        <div class="cards">
            <ModuleCard
                v-for="module in modules"
                :key="module.name"
                :module="module"
            />
        </div>
    </div>
</template>

<script>
import ModuleCard from '@/components/ModuleCard.vue';
import { get } from '@/modules/request.js';

export default {
    name: 'ModuleSelection',
    components: {
        ModuleCard
    },
    data() {
        return {
            modules: []
        }
    },
    async mounted() {
        const { house } = this.$store.state;

        if (house === null) {
            this.$router.push('house-selection');
        } else {
            const modules = Object
                .entries(await get(`/modules/${house}`))
                .reduce((accumulator, [key, description]) => {
                    if (description !== null) {
                        accumulator.push({
                            name: key,
                            description
                        });
                    }

                    return accumulator;
                }, []);

            this.modules = modules;
        }
    }
};
</script>

<style lang="scss" scoped>
.cards {
    gap: 16px;
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(520px, 1fr));
}
</style>