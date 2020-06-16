<template>
    <div :class="['message', type]">
        <h2 class="message__title">{{ title }}</h2>
        <p class="message__text">{{ text }}</p>
    </div>
</template>

<script>
export default {
    name: 'Message',
    props: {
        title: {
            type: String,
            required: true
        },
        text: {
            type: String,
            required: true
        },
        type: {
            type: String,
            required: true
        }
    }
};

export const MessageType = Object.freeze({
    Info: 'message--info',
    Error: 'message--error'
});
</script>

<style lang="scss" scoped>
.message {
    --background: #eeeeee;
    --color: #111111;

    padding: 16px;
    gap: 8px;
    border: 2px solid var(--color);
    border-radius: 4px;
    display: grid;
    grid-auto-flow: max-content;
    background: var(--background);
    color: var(--color);
    box-shadow: 0 4px 8px -2px var(--background);

    &--info {
        --background: #dcf0fa;
        --color: #3755ca;
    }

    &--error {
        --background: #fadcdc;
        --color: #bd2222;
    }

    &__title {
        margin: 0;
        font-size: 20px;
        font-weight: 500;
    }

    &__text {
        margin: 0;
        font-size: 14px;
    }
}
</style>