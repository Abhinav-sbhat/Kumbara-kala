package com.kumbarakala.app.data.repository;

import android.content.Context;
import com.kumbarakala.app.data.local.dao.StoryCardDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class CardRepository_Factory implements Factory<CardRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<StoryCardDao> storyCardDaoProvider;

  public CardRepository_Factory(Provider<Context> contextProvider,
      Provider<StoryCardDao> storyCardDaoProvider) {
    this.contextProvider = contextProvider;
    this.storyCardDaoProvider = storyCardDaoProvider;
  }

  @Override
  public CardRepository get() {
    return newInstance(contextProvider.get(), storyCardDaoProvider.get());
  }

  public static CardRepository_Factory create(Provider<Context> contextProvider,
      Provider<StoryCardDao> storyCardDaoProvider) {
    return new CardRepository_Factory(contextProvider, storyCardDaoProvider);
  }

  public static CardRepository newInstance(Context context, StoryCardDao storyCardDao) {
    return new CardRepository(context, storyCardDao);
  }
}
