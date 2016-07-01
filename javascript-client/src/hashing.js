import bcrypt from 'bcryptjs'

// Background resolve/reject determiner function.
const end = (resolve, reject) => function (err, value) {
  if (err) {
    reject(err)
  } else {
    resolve(value)
  }
}

/**
 * @param password [string] - password to be hashed
 * @return [Promise] - promise containing the hashed password
 */
export const hash = password => new Promise(
  function executor (resolve, reject) {
    bcrypt.genSalt(function (err, salt) {
      if (err) {
        reject(err)
      } else {
        bcrypt.hash(password, salt, end(resolve, reject))
      }
    })
  }
)

/**
 * @param plainPass [string] - submitted password (plaintext) to compare to reference
 * @param hashPass [string] - reference password (previously hashed)
 * @return [Promise] - promise containing comparison result as boolean (true if successful, false otherwise)
 */
export const compare = (plainPass, hashPass) => new Promise(
  function executor (resolve, reject) {
    bcrypt.compare(plainPass, hashPass, end(resolve, reject))
  }
)

export default {
  hash,
  compare
}
