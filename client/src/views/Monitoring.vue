<template>
    <div class="content">
        <Message
            v-if="error !== null"
            :title="'Произошла ошибка'"
            :text="error.message"
            :type="messageType.Error"
        />
        <PowerSupply
            v-if="currentModule === 'powerSupply'"
            :data="journal"
        />
        <WaterSupply
            v-if="currentModule === 'waterSupply'"
            :data="journal"
        />
        <ClimateSupply
            v-if="currentModule === 'climateSupply'"
            :data="journal"
        />
        <EquipmentSupply
            v-if="currentModule === 'equipmentSupply'"
            :data="journal"
        />
        <HouseholdApplianceSupply
            v-if="currentModule === 'householdApplianceSupply'"
            :data="journal"
        />
        <VideoMonitoringSupply
            v-if="currentModule === 'videoMonitoringSupply'"
            :data="journal"
        />
        <SecuritySupply
            v-if="currentModule === 'securitySupply'"
            :data="journal"
        />
        <ControlSupply
            v-if="currentModule === 'controlSupply'"
            :data="journal"
        />
    </div>
</template>

<script>
import Message, { MessageType } from '@/components/Message.vue';
import PowerSupply from '@/views/PowerSupply.vue';
import WaterSupply from '@/views/WaterSupply.vue';
import ClimateSupply from '@/views/ClimateSupply.vue';
import EquipmentSupply from '@/views/EquipmentSupply.vue';
import HouseholdApplianceSupply from '@/views/HouseholdApplianceSupply.vue';
import VideoMonitoringSupply from '@/views/VideoMonitoringSupply.vue';
import SecuritySupply from '@/views/SecuritySupply.vue';
import ControlSupply from '@/views/ControlSupply.vue';
import { get } from '@/modules/request.js';

export default {
    name: 'Monitoring',
    components: {
        Message,
        PowerSupply,
        WaterSupply,
        ClimateSupply,
        EquipmentSupply,
        HouseholdApplianceSupply,
        VideoMonitoringSupply,
        SecuritySupply,
        ControlSupply
    },
    data() {
        return {
            journal: [],
            error: null,
            messageType: MessageType
        }
    },
    computed: {
        currentModule() {
            return this.$store.state.module;
        }
    },
    async mounted() {
        if (this.$store.state.module === null) {
            this.$router.push('module-selection');
        } else {
            try {
                this.journal = await get(`/modules/${this.$store.state.house}/${this.$store.state.module}`);
            } catch (error) {
                this.error = error;
            }
        }
    }
};
</script>