<template>
    <div class="content">
        <Message
            v-if="error !== null"
            :title="'Произошла ошибка'"
            :text="error.message"
            :type="messageType.Error"
        />
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
import Message, { MessageType } from '@/components/Message.vue';
import { get } from '@/modules/request.js';

export default {
    name: 'HomeSelection',
    components: {
        HouseCard,
        Message
    },
    data() {
        return {
            houses: [],
            error: null,
            messageType: MessageType
        }
    },
    async mounted() {
        try {
            this.houses = await get('/houses');
        } catch (error) {
            this.error = error;
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