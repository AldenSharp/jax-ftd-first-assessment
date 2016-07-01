import net from 'net'
import vorpal from 'vorpal'

import { hash, compare } from './hashing'

let server

let user = ''

// Temporary fields - these are placeholders for values to be sent to Java instead.
// These will be submitted as JSON objects and handled by JAXB.
let users = {}
let fials = {}

const cli = vorpal()

cli.delimiter('>')

const register = cli.command('register <username> <password>')
register
  .description('Create a new user account.')
  .action(function (args, cb) {
    return (
      Promise.resolve(users[args.username] !== undefined) // is user already registered? TODO: Get from Java as string.
      .then(
        (alreadyRegistered) =>
          alreadyRegistered
            ? this.log('Registration error: username already taken.')
            : hash(args.password)
              .then((hashedPassword) => users[args.username] = hashedPassword) // TODO: Submit to Java as object.
              .then(() => this.log('Account created!'))
      )
      .catch((err) => this.log(`An error occurred: ${err}`))
    )
  })

const login = cli.command('login <username> <password>')
login
  .description('Log in with a username and password.')
  .action(function (args, cb) {
    return (
      Promise.resolve(users[args.username]) // TODO: Get from Java as string.
      .then(
        (hashedPassword) =>
          hashedPassword === undefined
            ? this.log('Login error: Username or password incorrect.')
            : compare(args.password, hashedPassword)
              .then((correctPassword) => correctPassword
                ? Promise.resolve(args.username)
                    .then((username) => user = username) // Set user as logged in
                    .then(() => this.log(`Logged in as ${user}.`))
                : this.log('Login error: Username or password incorrect.')
              )
      )
      .catch((err) => this.log(`An error occurred: ${err}`))
    )
  })

const files = cli.command('files')
files
  .description('Retrive a list of files stored by the user. (Must be logged in.)')
  .action(function (args, cb) {
    return (
      Promise.resolve(user)
      .then(
        (isLoggedIn) => isLoggedIn
          ? this.log('You have input the \'files\' command!')
          // TODO: Retrieve a list of files previously stored by the user.
          // TODO: Display the file ids and paths as stored in the database.
          : this.log('Error: You must be logged in to run this command.')
      )
    )
  })

const upload = cli.command('upload <local file path> [alternate path]')
upload
  .description('Submit a file to the server, from the given file path. (Must be logged in.)')
  .action(function (args, cb) {
    return (
      Promise.resolve(user)
      .then(
        (isLoggedIn) => isLoggedIn
          ? Promise.resolve(args['local file path'])
            // TODO: Select a local file based on the local file path.
            // TODO: Read the file and send it to the server.
            // TODO: Allow the user the option to save the file in the database under a different specified path.
            // TODO: (Bonus) Implement vorpal autocomplete for local files.
            .then(`You have input the \'upload\' command with argument ${args['local file path']}!`)
          : this.log('Error: You must be logged in to run this command.')
      )
    )
  })

const download = cli.command('download <database file id> [localPath]')
download
  .description('Retrive a file from server. Store it in the provided local path if given, otherwise in the original file path. (Must be logged in.)')
  .action(function (args, cb) {
    return (
      Promise.resolve(user)
      .then(
        (isLoggedIn) => isLoggedIn
          ? this.log(`You have input the \'download\' command with argument ${args['database file id']}!`)
          // TODO: Request a file from the server with the specified id.
          // TODO: By default, store that file locally under the path stored in the database.
          // TODO: Allow the user the option to specify an alternate local path.
          : this.log('Error: You must be logged in to run this command.')
      )
    )
  })

const logout = cli.command('logout')
logout
  .description('Log out the current user. (Must be logged in.)')
  .action(function (args, cb) {
    return (
      Promise.resolve(user)
      .then(
        (isLoggedIn) => isLoggedIn
          ? Promise.resolve()
              .then(() => user = '')
              .then(this.log('Logged out.'))
          : this.log('Error: You must be logged in to run this command.')
      )
    )
  })

// Temporary debugging command
const ls = cli.command('ls')
ls
  .description('Displays the user/password object')
  .action(function (args, cb) {
    this.log(users)
    cb()
  })

export default cli
