<template>
    <div class="content">
        <Message
            v-if="error !== null"
            :title="'Произошла ошибка'"
            :text="error.message"
            :type="messageType.Error"
        />
        <Message
            v-if="modules.length === 0 && !loading"
            :title="'Сообщение'"
            :text="'У выбранного дома нету модулей'"
            :type="messageType.Info"
        />
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
import Message, { MessageType } from '@/components/Message.vue';
import { get } from '@/modules/request.js';

export default {
    name: 'ModuleSelection',
    components: {
        ModuleCard,
        Message
    },
    data() {
        return {
            loading: true,
            modules: [],
            error: null,
            messageType: MessageType
        }
    },
    async mounted() {
        const { house } = this.$store.state;

        if (house === null) {
            this.$router.push('house-selection');
        } else {
            try {
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
            } catch (error) {
                this.error = error;
            }

            this.loading = false;
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