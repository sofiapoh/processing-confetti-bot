const config = require('./config.js');
const Twit = require('twit');
const exec = require('child_process').exec;
const fs = require('fs');
const T = new Twit(config);

exports.tweetConfetti = (content) => {

  const cmd = 'open -a create_confetti.app';

  exec(cmd, makeConfetti);

  function makeConfetti() {
    const filename = './src/process/confetti.gif';
    const params = {
      encoding: 'base64',
    };
    const b64 = fs.readFileSync(filename, params);

    T.post('media/upload', { media_data: b64 }, onFileUploadReady);

    function onFileUploadReady(err, data, response) {
      if (err) throw err;

      const id = data.media_id_string;
      const tweet = {
        status: content,
        media_ids: [id],
      };
      T.post('statuses/update', tweet, onTweetDone);
    }

    function onTweetDone(err, data, response) {
      if (err) {
        throw err;
      } else {
        console.log('Tweet sent');
      }
    }
  }
}
