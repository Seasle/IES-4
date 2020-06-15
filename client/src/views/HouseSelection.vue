<template>
    <div class="content">
        <div class="cards">
            <HouseCard
                v-for="house in houses"
                :key="house.id"
                :house="house"
            />
        </div>
    </div>
</template>

<script>
import HouseCard from '@/components/HouseCard.vue';
import { get } from '@/modules/request.js';

export default {
    name: 'HomeSelection',
    components: {
        HouseCard
    },
    data() {
        return {
            houses: []
        }
    },
    async mounted() {
        this.houses = await get('/houses');
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