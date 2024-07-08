const express = require('express');
const router = express.Router();
const extensionBlockService = require('../services/extension-block-service');

router.get('/fixed-extensions/:userId', async (req, res) => {
    try {
        const userId = req.params.userId;
        const extensionData = await extensionBlockService.getFixedExtensions(userId);
        res.status(200).json(extensionData);
    } catch (error) {
        console.log(error.message);
        res.status(500).send(error.message);
    }
});

module.exports = router;
