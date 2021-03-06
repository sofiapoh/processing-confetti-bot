const config = require('./src/config.js');
const Twit = require('twit');
const tweet = require('./src/tweet.js');
const exec = require('child_process').exec;
const fs = require('fs');

const T = new Twit(config);

const stream = T.stream('user');

const onMention = (reply) => {
  const replyTo = reply.in_reply_to_screen_name;
  const text = reply.text;
  const fromUser = reply.user.screen_name;

  const regex = /@confetti_bot/g;

  if (replyTo === 'confetti_bot' || regex.test(text.toLowerCase())) {
    const tweetContent = `Hey @${fromUser}, have some confetti! ✨`;

    tweet.tweetConfetti(tweetContent);
  }
};

stream.on('tweet', onMention);
