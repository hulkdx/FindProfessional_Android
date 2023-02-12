import SwiftUI
import shared

#if DEBUG
struct SignUpScreen_Previews: PreviewProvider {
   static var previews: some View {
       SignUpScreen()
   }
}
#endif

struct SignUpScreen: View {
    @StateObject
    private var viewModel: SignUpViewModel = SignUpViewModel(KoinHelper().signUpUseCase)
    
    var body: some View {
        VStack(spacing: 0) {
            EmailTextField(value: $viewModel.email)
            PasswordTextField(value: $viewModel.password)
                .padding(.top, 16)
            SignUpButton() {
                viewModel.onSubmitClicked()
            }
                .padding(.top, 16)
        }
        .padding(.horizontal, 16)
        .onDisappear { viewModel.onDisappear() }
        .snackbar(message: $viewModel.error)
    }
}

private struct SignUpButton: View {
    
    let action: () -> Void

    var body: some View {
        FilledButton(text: MR.strings().signUp.desc().localized(), action: action)
    }
}
