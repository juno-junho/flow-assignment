const FixedFileExtension = require('../../../models/fixed-file-extension');

const getFixedExtensions = async (userId) => {
    const fixedExtensions = FixedFileExtension.findAll({
        where: {
            userId: userId
        }
    });
    return (await fixedExtensions).map(extension => ({
        "extension": extension.fileExtension,
        "isChecked": extension.restricted
    }));
};

module.exports = {getFixedExtensions}
