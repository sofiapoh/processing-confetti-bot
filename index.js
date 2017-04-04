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

  if (replyTo === 'confetti_bot') {
    const tweetContent = `Hey @${fromUser}, here is some confetti`;

    tweet.tweetConfetti(tweetContent);
  }
};

stream.on('tweet', onMention);
