<template>
    <div class="wrapper">
        <List :items="items">
            <template v-slot="{ item }">
                <strong>{{ item.timestamp }}</strong>: {{ item.message }}
            </template>
        </List>
    </div>
</template>

<script>
import List from '@/components/List.vue';
import format from '@/modules/format.js';

export default {
    name: 'ControlSupply',
    props: ['data'],
    components: {
        List
    },
    computed: {
        items() {
            return this.data.map(entry => ({
                timestamp: format(entry.timestamp, '[day].[month].[year] [hour]:[minute]:[second]'),
                message: entry.message
            }));
        }
    }
};
</script>