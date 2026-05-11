package com.kumbarakala.app.ui.viewmodel;

import android.content.Context;
import com.kumbarakala.app.data.repository.AuthRepository;
import com.kumbarakala.app.data.repository.CardRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class MyCardsViewModel_Factory implements Factory<MyCardsViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<CardRepository> cardRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public MyCardsViewModel_Factory(Provider<Context> contextProvider,
      Provider<CardRepository> cardRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.cardRepositoryProvider = cardRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public MyCardsViewModel get() {
    return newInstance(contextProvider.get(), cardRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static MyCardsViewModel_Factory create(Provider<Context> contextProvider,
      Provider<CardRepository> cardRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new MyCardsViewModel_Factory(contextProvider, cardRepositoryProvider, authRepositoryProvider);
  }

  public static MyCardsViewModel newInstance(Context context, CardRepository cardRepository,
      AuthRepository authRepository) {
    return new MyCardsViewModel(context, cardRepository, authRepository);
  }
}
