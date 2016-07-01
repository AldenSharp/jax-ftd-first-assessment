import net from 'net'
import fs from 'fs'
import vorpal from 'vorpal'
import { hash, compare } from './hashing'
import { writeMessage, test, isRegistered } from './functions'

// initialize constants
const cli = vorpal()
const host = 'localhost'
const port = 667

let users = {}

const openConnection = () => {
  server = net.createConnection(port, host, () => {
    return 0
  })
}

const closeConnection = () => {
  server.end()
}

const writeStr = (string) => server.write(string + '\n')

const writeObj = (key, object) => {
  server.write(JSON.stringify({ key: object }) + '\n')
}

// initalize variables
let server
let user = '' // Empty string (falsy) when not logged in. Takes the user's username (truthy) when logged in.

cli.delimiter('>')

// sample test command
const hi = cli.command('hi')
hi
  .description('Sample test command')
  .action(function (args, callback) {
    let output = test()
    this.log('Function output: ' + output)
    callback()
  })

// sample test command
const finduser = cli.command('finduser <username>')
finduser
  .description('Sample test command')
  .action(function (args, callback) {
    return (
      Promise.resolve(isRegistered(args['username']))
      .then(
        (Registered) => this.log('Function output: ' + Registered)
      )
    )
  })

// Register command
const register = cli.command('register <username> <password>')
register
  .description('Create a new user account.')
  .action(function (args, cb) {
    return (
      Promise.resolve(isRegistered(args['username']))
      .then(
        (Registered) =>
          Registered
            ? this.log('Registration error: username already taken.')
            : hash(args.password)
              .then((hashedPassword) => writeMessage('register', args['username'] + ' ' + args['password']))
              .then(() => this.log('Account created!'))
      )
      .catch((err) => this.log(`An error occurred: ${err}`))
    )
  })

// Login command
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

// Files command
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

// Upload command
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

// Download command
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

// Logout command
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

// Temporary command for debugging
const ls = cli.command('ls')
ls
  .description('Displays the user/password object')
  .action(function (args, cb) {
    this.log(users)
    cb()
  })

export default cli
